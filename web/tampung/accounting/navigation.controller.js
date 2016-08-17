(function() {
    'use strict';

    angular
        .module('web')
        .controller('NavigationController', NavigationController);

    /** @ngInject */
    function NavigationController($uibModal, $state, $scope) {

        $scope.state = $state;

        $scope.menus = [{
            'link': 'app.mainmenu.supplier.table',
            'title': 'Supplier',
            'text': 'Daftar Supplier untuk keperluan Pembelian secara Utang'
        }, {
            'link': 'app.mainmenu.customer.table',
            'title': 'Pelanggan',
            'text': 'Daftar Customer untuk keperluan Penjualan secara Piutang'
        }, {
            'link': 'app.mainmenu.expensecategory.table',
            'title': 'Kategori Biaya',
            'text': 'Daftar kategori biaya'
        }, {
            'link': 'app.mainmenu.incomecategory.table',
            'title': 'Kategori Pendapatan',
            'text': 'Daftar kategori pendapatan'
        }, {
            'link': 'app.mainmenu.catalogcash.table',
            'title': 'Katalog Kas',
            'text': 'Daftar katalog Kas'
        }, {
            'link': 'app.mainmenu.catalogbank.table',
            'title': 'Katalog Bank',
            'text': 'Daftar katalog Bank'
        }, {
            'link': 'app.mainmenu.adjustment.table',
            'title': 'Penyesuaian',
            'text': 'Penyesuaian biaya tenaga kerja dan overhead pada proses produksi'
        }, {
            'link': 'app.mainmenu.linkaccount.table',
            'title': 'Link Account',
            'text': 'Hubungan account'
        }, {
            'link': 'app.mainmenu.unit.table',
            'title': 'Unit',
            'text': 'Daftar Satuan yang digunakan pada finish goods dan raw material'
        }, {
            'link': 'app.mainmenu.unitconvertion.table',
            'title': 'Unit Konversi',
            'text': 'Konversi Unit'
        }, {
            'link': 'app.mainmenu.user.table',
            'title': 'User',
            'text': 'Daftar User'
        }, {
            'link': 'app.mainmenu.role.table',
            'keyword': 'jabatan group user',
            'title': 'Role',
            'text': 'Daftar Role'
        }, {
            'link': 'app.mainmenu.authority.table',
            'keyword': 'Hak akses, Access Right',
            'title': 'Authority',
            'text': 'Daftar Hak Akses User'
        }, {
            'link': 'app.mainmenu.companyprofile.table',
            'keyword': 'Data informasi perusahaan',
            'title': 'Company Profile',
            'text': 'Data informasi perusahaan'
        }, {
            'link': 'app.mainmenu.account.table',
            'keyword': 'Account',
            'title': 'Akun',
            'text': 'Daftar Account'
        }, {
            'link': 'app.mainmenu.journal.table',
            'keyword': 'Journal jurnal umum',
            'title': 'Jurnal',
            'text': 'Transaksi Jurnal Umum'
        }, {
            'link': 'app.mainmenu.purchase.input',
            'keyword': 'Purchase Pembelian',
            'title': 'Pembelian',
            'text': 'Pembelian Raw Material'
        }, {
            'link': 'app.mainmenu.sale.input',
            'keyword': 'Sale Penjualan',
            'title': 'Penjualan',
            'text': 'Penjualan Finish Goods'
        }, {
            'link': 'app.mainmenu.rawmaterial.table',
            'keyword': 'Daftar Raw Material',
            'title': 'Daftar Bahan Baku',
            'text': 'Pendaftaran Raw Material'
        }, {
            'link': 'app.mainmenu.finishedgoods.table',
            'keyword': 'Daftar Finish Goods',
            'title': 'Daftar Barang Jadi',
            'text': 'Pendaftaran Finish Goods'
        }, {
            'link': 'app.mainmenu.template.table',
            'keyword': 'Module jurnal khusus template',
            'title': 'Template',
            'text': 'Module Transaksi Jurnal Khusus'
        }, {
            'link': 'app.mainmenu.wip.table',
            'keyword': 'WIP Work In Process',
            'title': 'Produksi',
            'text': 'Module Transaksi Work In Process'
        }, {
            'link': 'app.mainmenu.inventory.table',
            'keyword': 'WIP Work In Process',
            'title': 'Inventory',
            'text': 'Module Transaksi Work In Process'
        }];

        $scope.onSelect = function(item) {
            $state.go(item.link);
            // $scope.menuSelected = undefined;
        }

    }
})();
