(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.authority', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/authority/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.authority.table', {
            url: '/authority',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/authority/table/table.html'
                }
            }

        })

        .state('app.mainmenu.authority.link', {
            url: '/authority/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/authority/link/link.html'
                }
            }

        })

    }

})();
