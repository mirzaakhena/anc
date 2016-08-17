(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('app.mainmenu.wip', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/wip/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.wip.table', {
            url: '/wip',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/wip/table/table.html',
                    controller: 'WIPTableController'
                }
            }

        })

        .state('app.mainmenu.wip.link', {
            url: '/wip/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/wip/link/link.html'
                }
            }

        })

    }

})();
