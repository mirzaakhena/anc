(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.production', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/production/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.production.table', {
            url: '/production',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/production/table/table.html'
                }
            }

        })

        .state('app.mainmenu.production.link', {
            url: '/production/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/production/link/link.html'
                }
            }

        })

    }

})();
