package eu.anmore.emed;

/**
 * Service result.
 * 
 * @author glipecki
 * 
 */
public class ServiceResult {

	/**
	 * Successful service result result.
	 * 
	 * @return
	 */
	public static ServiceResult ok() {
		return new ServiceResult(ServiceStatus.OK);
	}

	/**
	 * Deserialization constructor.
	 * 
	 * @deprecated deserialization constructor
	 */
	@Deprecated
	public ServiceResult() {
	}

	public ServiceResult(final ServiceStatus status) {
		this.status = status;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(final ServiceStatus status) {
		this.status = status;
	}

	private ServiceStatus status;

}
