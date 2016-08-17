(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        $stateProvider.state('app.home', {
            url: '/home',
            views: {
                'header@app': {
                    template: '<h1>Ini sesuatu</h1>',
                },
                'content@app': {
                    template: '<h1>Apa ya ini</h1>',
                },
                'footer@app': {
                    template: '<h1>Hahaha</h1>',
                }

            },
        });
    }

})();
