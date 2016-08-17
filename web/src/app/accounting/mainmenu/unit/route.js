(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('app.mainmenu.unit', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/unit/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.unit.table', {
            url: '/unit',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/unit/table/table.html',
                    controller: 'UnitTableController'
                }
            }

        })

        .state('app.mainmenu.unit.link', {
            url: '/unit/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/unit/link/link.html'
                }
            }

        })

    }

})();
