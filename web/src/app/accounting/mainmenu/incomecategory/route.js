(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.incomecategory', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/incomecategory/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.incomecategory.table', {
            url: '/incomecategory',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/incomecategory/table/table.html',
                    controller: 'IncomeCategoryController'
                }
            }

        })

        .state('app.mainmenu.incomecategory.link', {
            url: '/incomecategory/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/incomecategory/link/link.html'
                }
            }

        })

    }

})();
