(function() {
    'use strict';

    angular
        .module('web')
        .controller('WIPTableController', WIPTableController);

    /** @ngInject */
    function WIPTableController($uibModal, $state, $scope, WIPService) {

        $scope.create = create;
        $scope.detail = detail;
        $scope.finishProduction = finishProduction;
        $scope.continueProduction = continueProduction;

        function reload() {
            $scope.items = WIPService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/wip/input/input.html',
                controller: 'WIPInputController',
            }).result.then(reload);
        }

        function detail(wip) {
            console.log(wip);
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/wip/detail/detail.html',
                controller: 'WIPDetailController',
                size: 'lg',
                resolve: {
                    wip: function() {
                        return wip;
                    }
                }
            }).result.then(reload);
        }

        function finishProduction(wip) {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/wip/finish/finish.html',
                controller: 'WIPFinishController',
                resolve: {
                    wip: function() {
                        return wip;
                    }
                }
            }).result.then(reload);
        }

        function continueProduction(index) {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/wip/continue/continue.html',
                controller: 'WIPContinueController',
            }).result.then(reload);
        }

        reload();

    }


})();
