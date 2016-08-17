(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('app.mainmenu.customer', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/customer/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.customer.table', {
            url: '/customer',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/customer/table/table.html',
                    controller: 'CustomerController'
                }
            }

        })

        .state('app.mainmenu.customer.link', {
            url: '/customer/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/customer/link/link.html'
                }
            }

        })

    }

})();
