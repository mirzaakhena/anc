(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.supplier', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/supplier/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.supplier.table', {
            url: '/supplier',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/supplier/table/table.html',
                    controller:'SupplierController'
                }
            }

        })

        .state('app.mainmenu.supplier.link', {
            url: '/supplier/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/supplier/link/link.html'
                }
            }

        })

    }

})();
