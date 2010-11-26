package de.scoopgmbh.bms.db.service.impl;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.model.Login;
import de.scoopgmbh.bms.db.model.Rolle;
import de.scoopgmbh.bms.db.service.LoginService;

public class LoginServiceImpl extends EbeanDao<Login> implements LoginService {

	@Inject
	public LoginServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	public Login getLoginByName(final String name) {
		return impl.getLoginByName(name);
	}

	@Transactional(rollbackFor = { java.lang.RuntimeException.class,
			java.lang.Exception.class })
	public Login getLoginById(final long loginId) {
		return impl.getLoginById(loginId);
	}

	public List<Login> getLoginsByAutoritaet(final long autoritaetId) {
		return impl.getLoginsByAutoritaet(autoritaetId);
	}

	public List<Login> getAllLogins() {
		return impl.getAllLogins();
	}

	public List<Rolle> getAllRoles() {
		return impl.getAllRoles();
	}

	public List<Rolle> getRolesForLoginId(final long loginId) {
		return impl.getRolesForLoginId(loginId);
	}

	@Transactional(readOnly = false)
	public void addRole(final Rolle r, final long loginId) {
		impl.addRole(r, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteRole(final Rolle r, final long loginId) {
		impl.deleteRole(r, loginId);
	}

	public Rolle getRole(final long rolleId) {
		return impl.getRole(rolleId);
	}

	@Transactional(readOnly = false)
	public void updateRole(final Rolle r, final long editorId) {
		impl.updateRole(r, editorId);
	}

	@Transactional(readOnly = false)
	public void addLogin(final Login l, final long editorId) {
		this.impl.addLogin(l, editorId);
	}

	@Transactional(readOnly = false)
	public void deleteLogin(final Login l, final long editorId) {
		this.impl.deleteLogin(l, editorId);

	}

	@Transactional(readOnly = false)
	public void disableLogin(final Login l, final long editorId) {
		this.impl.disableLogin(l, editorId);
	}

	@Transactional(readOnly = false)
	public void updateLogin(final Login lg, final long longinId) {
		this.impl.updateLogin(lg, longinId);
	}

	public long getAutoritaetenIdByAngestelltenId(final long angestelltenId)
			throws EntityNotFoundException {
		return this.impl.getAutoritaetenIdByAngestelltenId(angestelltenId);
	}

}
