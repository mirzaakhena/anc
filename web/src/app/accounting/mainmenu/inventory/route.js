(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.inventory', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/inventory/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.inventory.table', {
            url: '/inventory',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/inventory/table/table.html',
                    controller:'InventoryController'
                }
            }

        })

        .state('app.mainmenu.inventory.link', {
            url: '/inventory/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/inventory/link/link.html'
                }
            }

        })

    }

})();
