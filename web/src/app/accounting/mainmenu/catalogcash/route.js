(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.catalogcash', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/catalogcash/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.catalogcash.table', {
            url: '/catalogcash',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/catalogcash/table/table.html',
                    controller: 'CatalogCashController'
                }
            }

        })

        .state('app.mainmenu.catalogcash.link', {
            url: '/catalogcash/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/catalogcash/link/link.html'
                }
            }

        })

    }

})();
