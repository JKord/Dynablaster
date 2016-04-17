package jkord.dynablaster.web;

import jkord.dynablaster.util.HttpConnection;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.realtime.InstagramSubscription;
import org.jinstagram.realtime.SubscriptionType;
import org.springframework.http.MediaType;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/oauth")
public class OAuthController {


    // http://stackoverflow.com/questions/2168610/which-html-parser-is-the-best

    private String verifierCode;

    private final String
        apiKey = "230d5a4e14e04c82883bbe923b91a39d",
        apiSecret = "8c1f0c641720483f8adf553a993b2f6e",
        callback = "http://localhost:8080/oauth/instagram_callback",
        subscription = "http://localhost:8080/oauth/instagram_subscription";

    private Token accessToken;

    private InstagramService service = new InstagramAuthService()
        .apiKey(apiKey)
        .apiSecret(apiSecret)
        .callback(callback)
        .build();

    @RequestMapping(value = "/instagram_callback", method = RequestMethod.GET)
    public RedirectView instagramCallback(HttpServletRequest request, @RequestParam String code) {
        verifierCode = code;

        Verifier verifier = new Verifier(verifierCode);
        accessToken = service.getAccessToken(verifier);

        //https://api.instagram.com/oauth/authorize/?client_id=230d5a4e14e04c82883bbe923b91a39d&redirect_uri=http://localhost:8080/oauth/instagram_callback&response_type=token&scope=basic

        // https://api.instagram.com/oauth/access_token?client_id=230d5a4e14e04c82883bbe923b91a39d&client_secret=8c1f0c641720483f8adf553a993b2f6e&grant_type=authorization_code&redirect_uri=http://localhost:8080/oauth/instagram_callback&response_type=code

        return new RedirectView("/", true);
    }

    @RequestMapping(value = "/instagram_callback", method = RequestMethod.POST)
    public RedirectView instagramCallbackP(HttpServletRequest request) {
        // https://api.instagram.com/oauth/access_token?client_id=230d5a4e14e04c82883bbe923b91a39d&client_secret=8c1f0c641720483f8adf553a993b2f6e&grant_type=authorization_code&redirect_uri=http://localhost:8080/oauth/instagram_subscription&code=CODE

        return new RedirectView("/", true);
    }

    @RequestMapping(value = "/instagram_subscription", method = RequestMethod.POST)
    public String instagramSubscription(HttpServletRequest request) {

        return "1";
    }

    @RequestMapping(value = "/test/instagram", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MediaFeed testInstagram() {

        if (accessToken == null) {
            String authorizationUrl = service.getAuthorizationUrl();//.replace("response_type=code", "response_type=token");
            String strResponse = null;
            try {
                strResponse = HttpConnection.sendGet(authorizationUrl);
            } catch (Exception ignored) { }


        }

        Instagram instagram = new Instagram(accessToken);

        MediaFeed feed = null;
        try {
            UserInfo userInfo = instagram.getCurrentUserInfo();
            feed = instagram.getUserFeeds();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feed;
    }

    @RequestMapping(value = "/test/instagram_subscription", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testInstagramSubscription() {

        InstagramSubscription igSub = new InstagramSubscription()
            .clientId(apiKey)
            .clientSecret(apiSecret)
            .object(SubscriptionType.USERS)
            .objectId("jura_kor")
            .aspect("media")
            .callback("http://yourcallbackurl/handleSubscription")
            .verifyToken("londonTagSubscription");

        return "1";
    }
}
