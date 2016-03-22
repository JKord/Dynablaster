'use strict';

angular.module('dynablasterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('import', {
                parent: 'site',
                url: '/social/import',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/import/import.html',
                        controller: 'ImportController'
                    }
                },
                resolve: {

                }
            });
    });
