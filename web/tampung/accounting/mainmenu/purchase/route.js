(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.purchase', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/purchase/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.purchase.input', {
            url: '/purchase',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/purchase/table/table.html',
                    controller: 'PurchaseController'
                }
            }

        })

        .state('app.mainmenu.purchase.link', {
            url: '/purchase/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/purchase/link/link.html'
                }
            }

        })

    }

})();
