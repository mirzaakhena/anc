(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.linkaccount', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/linkaccount/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.linkaccount.table', {
            url: '/linkaccount',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/linkaccount/table/table.html'
                }
            }

        })

        .state('app.mainmenu.linkaccount.link', {
            url: '/linkaccount/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/linkaccount/link/link.html'
                }
            }

        })

    }

})();
