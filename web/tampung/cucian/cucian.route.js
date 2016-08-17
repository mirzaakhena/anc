(function() {
    'use strict';

    angular
        .module('web')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {

        // $stateProvider
        //     .state('app.cucian', {
        //         // abstract: true,
        //         views: {
        //             "content@app": {
        //                 templateUrl: 'app/cucian/navigasi.html',
        //             }
        //         }

        //     })

        // .state('app.cucian.masuk', {
        //     url: '/cucian/masuk',
        //     views: {
        //         "cucian@app.cucian": {
        //             templateUrl: 'app/cucian/masuk.html'
        //         }
        //     }
        // })

        // .state('app.cucian.proses', {
        //     url: '/cucian/proses',
        //     views: {
        //         "cucian@app.cucian": {
        //             templateUrl: 'app/cucian/proses.html'
        //         }
        //     }
        // })

        // .state('app.cucian.dirak', {
        //     url: '/cucian/dirak',
        //     views: {
        //         "cucian@app.cucian": {
        //             templateUrl: 'app/cucian/dirak.html'
        //         }
        //     }
        // })

        // .state('app.cucian.selesai', {
        //     url: '/cucian/selesai',
        //     views: {
        //         "cucian@app.cucian": {
        //             templateUrl: 'app/cucian/selesai.html'
        //         }
        //     }
        // })

        // .state('app.cucian.keluar', {
        //     url: '/cucian/keluar',
        //     views: {
        //         "cucian@app.cucian": {
        //             templateUrl: 'app/cucian/keluar.html'
        //         }
        //     }
        // })

    }

})();
