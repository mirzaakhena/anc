(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.account', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/account/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.account.table', {
            url: '/account',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/account/table/account.html',
                    controller:'AccountController'
                }
            }

        })

        .state('app.mainmenu.account.link', {
            url: '/account/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/account/link/link.html'
                }
            }

        })

    }

})();
