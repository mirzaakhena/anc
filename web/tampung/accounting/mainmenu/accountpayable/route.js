(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.accountpayable', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/accountpayable/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.accountpayable.table', {
            url: '/accountpayable',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/accountpayable/table/table.html',
                    controller:'AccountPayableTableController'
                }
            }

        })

        .state('app.mainmenu.accountpayable.link', {
            url: '/accountpayable/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/accountpayable/link/link.html'
                }
            }

        })

    }

})();
