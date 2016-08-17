(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.adjustment', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/adjustment/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.adjustment.table', {
            url: '/adjustment',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/adjustment/table/table.html'
                }
            }

        })

        .state('app.mainmenu.adjustment.link', {
            url: '/adjustment/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/adjustment/link/link.html'
                }
            }

        })

    }

})();
