(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.user', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/user/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.user.table', {
            url: '/user',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/user/table/table.html'
                }
            }

        })

        .state('app.mainmenu.user.link', {
            url: '/user/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/user/link/link.html'
                }
            }

        })

    }

})();
