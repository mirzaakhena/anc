(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.role', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/role/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.role.table', {
            url: '/role',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/role/table/table.html'
                }
            }

        })

        .state('app.mainmenu.role.link', {
            url: '/role/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/role/link/link.html'
                }
            }

        })

    }

})();
