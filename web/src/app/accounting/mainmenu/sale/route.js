(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.sale', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/sale/tab-title.html'
                }
            }

        })

         .state('app.mainmenu.sale.input', {
            url: '/sale',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/sale/table/table.html',
                    controller: 'SaleController'
                }
            }

        })

        .state('app.mainmenu.sale.link', {
            url: '/sale/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/sale/link/link.html'
                }
            }

        })

    }

})();
