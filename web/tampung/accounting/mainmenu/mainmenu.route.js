(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider

        .state('app.mainmenu', {
            abstract: true,
            views: {
                "content@app": {
                    templateUrl: 'app/accounting/mainmenu/mainmenu.html',
                    controller: 'MainMenuController'
                }
            }

        })

    }

})();
