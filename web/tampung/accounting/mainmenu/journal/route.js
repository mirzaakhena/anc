(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('app.mainmenu.journal', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/journal/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.journal.table', {
            url: '/journal',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/journal/table/journal.html',
                    controller: 'JournalController'
                }
            }

        })

        .state('app.mainmenu.journal.link', {
            url: '/journal/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/journal/link/link.html'
                }
            }

        })

    }

})();
