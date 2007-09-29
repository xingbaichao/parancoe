/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.po.Jugger;

import org.apache.log4j.Logger;

/**
 * General porpouse BO.
 * @author Enrico Giurin
 *
 */
public class TrustBo {
	/**
	 * min value for threshold access.
	 */
	public static final double MIN_THRESHOLD_ACCESS = 0d;

	/**
	 * Max value for threshold access
	 */
	public static final double MAX_THRESHOLD_ACCESS = 1d;

	private static final Logger logger = Logger.getLogger(TrustBo.class);

	private double thresholdAccess;

	public double getThresholdAccess() {
		return thresholdAccess;
	}

	public void setThresholdAccess(double thresholdAccess) {
		this.thresholdAccess = thresholdAccess;
	}
	
	/**
	 * Check if jugger is enabled to update his JUG attribute.
	 * @param jugger
	 * @return
	 */
	 public  boolean canJuggerEditJUG(Jugger jugger)
	{
		double reliability = jugger.getReliability();
		if(reliability < MIN_THRESHOLD_ACCESS || reliability > MAX_THRESHOLD_ACCESS)
		{
			throw new IllegalArgumentException("reliability: "+reliability+" is out of range");
		}
		if(thresholdAccess < MIN_THRESHOLD_ACCESS || thresholdAccess > MAX_THRESHOLD_ACCESS)
		{
			throw new IllegalArgumentException("thresholdAccess: "+thresholdAccess+" is out of range");
		}
		if(jugger.getReliability()>= thresholdAccess)
			return true;
		
		return false;
	}
    

}
