package com.mirzaakhena.batchsystem.service;


//@Service
//public class PrimaryKeyTableService {

//	@Autowired
//	private PrimaryKeyTableDao dao;
//
//	public Long getNextId(Class<?> klazz, Client client) {
//		PrimaryKeyTable pkt = dao.findOneByTableNameAndClientId(klazz.getSimpleName(), client.getId());
//		if (pkt == null) {
//			pkt = new PrimaryKeyTable();
//			pkt.setClient(client);
//			pkt.setNextSequenceId(1L);
//			pkt.setTableName(klazz.getSimpleName());
//			dao.save(pkt);
//		}
//		return pkt.getNextSequenceId();
//	}
//
//	public void incrementNextId(Class<?> klazz, Client client) {
//		PrimaryKeyTable pkt = dao.findOneByTableNameAndClientId(klazz.getSimpleName(), client.getId());
//		if (pkt != null) {
//			pkt.setNextSequenceId(pkt.getNextSequenceId() + 1L);
//			dao.save(pkt);
//		}
//	}

//}
