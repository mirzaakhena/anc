(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu.accountreceivable', {
            abstract: true,
            views: {
                "mainmenu@app.mainmenu": {
                    templateUrl: 'app/accounting/mainmenu/accountreceivable/tab-title.html'
                }
            }

        })

        .state('app.mainmenu.accountreceivable.table', {
            url: '/accountreceivable',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/accountreceivable/table/table.html',
                    controller:'AccountReceivableTableController'
                }
            }

        })

        .state('app.mainmenu.accountreceivable.link', {
            url: '/accountreceivable/link',
            views: {
                "tab-content": {
                    templateUrl: 'app/accounting/mainmenu/accountreceivable/link/link.html'
                }
            }

        })

    }

})();
