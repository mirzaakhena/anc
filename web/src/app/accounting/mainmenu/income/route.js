(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.income', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/income/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.income.table', {
            url: '/income',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/income/table/table.html'
                }
            }

        })

        .state('app.mainmenu.income.link', {
            url: '/income/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/income/link/link.html'
                }
            }

        })

    }

})();
