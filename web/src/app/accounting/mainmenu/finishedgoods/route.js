(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('app.mainmenu.finishedgoods', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/finishedgoods/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.finishedgoods.table', {
            url: '/finishedgoods',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/finishedgoods/table/table.html',
                    controller: 'FinishedGoodsController'
                }
            }

        })

        .state('app.mainmenu.finishedgoods.link', {
            url: '/finishedgoods/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/finishedgoods/link/link.html'
                }
            }

        })

    }

})();
