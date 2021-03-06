package net.gcicom.cdr.processor.repository.imported.events;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import net.gcicom.domain.imported.events.AuditEvent;


public interface CDRAuditRepository extends CrudRepository<AuditEvent, BigInteger> {

}
