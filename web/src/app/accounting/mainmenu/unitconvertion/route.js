(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.unitconvertion', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/unitconvertion/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.unitconvertion.table', {
            url: '/unitconvertion',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/unitconvertion/table/table.html'
                }
            }

        })

        .state('app.mainmenu.unitconvertion.link', {
            url: '/unitconvertion/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/unitconvertion/link/link.html'
                }
            }

        })

    }

})();
