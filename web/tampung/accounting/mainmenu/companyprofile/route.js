(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.companyprofile', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/companyprofile/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.companyprofile.table', {
            url: '/companyprofile',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/companyprofile/table/table.html'
                }
            }

        })

        .state('app.mainmenu.companyprofile.link', {
            url: '/companyprofile/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/companyprofile/link/link.html'
                }
            }

        })

    }

})();
