(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('app.generalledger', {
                url:'/generalledger',
                views: {
                    "content@app": {
                        templateUrl: 'app/accounting/generalledger/general-ledger.html',
                        controller: 'GeneralLedgerController'
                    }
                }

            })

        

    }

})();
