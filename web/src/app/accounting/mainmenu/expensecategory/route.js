(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.expensecategory', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/expensecategory/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.expensecategory.table', {
            url: '/expensecategory',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/expensecategory/table/table.html',
                    controller:'ExpenseCategoryController'
                }
            }

        })

        .state('app.mainmenu.expensecategory.link', {
            url: '/expensecategory/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/expensecategory/link/link.html'
                }
            }

        })

    }

})();
