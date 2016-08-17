(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.rawmaterial', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/rawmaterial/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.rawmaterial.table', {
            url: '/rawmaterial',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/rawmaterial/table/table.html',
                    controller:'RawMaterialController'
                }
            }

        })

        .state('app.mainmenu.rawmaterial.link', {
            url: '/rawmaterial/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/rawmaterial/link/link.html'
                }
            }

        })

    }

})();
