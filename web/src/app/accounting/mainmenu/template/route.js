(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.template', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/template/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.template.table', {
            url: '/template',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/template/table/table.html'
                }
            }

        })

        .state('app.mainmenu.template.link', {
            url: '/template/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/template/link/link.html'
                }
            }

        })

    }

})();
