(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.expense', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/expense/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.expense.table', {
            url: '/expense',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/expense/table/table.html'
                }
            }

        })

        .state('app.mainmenu.expense.link', {
            url: '/expense/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/expense/link/link.html'
                }
            }

        })

    }

})();
