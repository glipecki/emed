package eu.anmore.emed.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import eu.anmore.emed.authentication.User;

/**
 * User granted authorities resolver.
 * <p>
 * TODO (Grzegorz Lipecki): implement hierarchy resolving
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public class GrantedAuthoritiesResolver {

	GrantedAuthoritiesResolver(User user) {
		this.user = user;
	}

	List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.addAll(getAuthoritiesFromPermissions());
		return Collections.unmodifiableList(grantedAuthorities);
	}

	private List<GrantedAuthority> getAuthoritiesFromPermissions() {
		List<GrantedAuthority> permissionAsGrantedAuthorities = new ArrayList<GrantedAuthority>();
		for (String permission : user.getPermissions()) {
			permissionAsGrantedAuthorities.add(new SimpleGrantedAuthority(permission));
		}
		return permissionAsGrantedAuthorities;
	}

	private final User user;

}
