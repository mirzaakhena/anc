(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.catalogbank', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/catalogbank/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.catalogbank.table', {
            url: '/catalogbank',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/catalogbank/table/table.html',
                    controller: 'CatalogBankController'
                }
            }

        })

        .state('app.mainmenu.catalogbank.link', {
            url: '/catalogbank/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/catalogbank/link/link.html'
                }
            }

        })

    }

})();
