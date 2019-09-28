/********  Ntuple.java **********
 *  
 *  APCS Project Spring 2011-2017
 *  Computational Biology
 *  Dr. John Pais 
 *  pais.john@gmail.com
 *
 */

public class Ntuple 
{

	private Object[] objList;
	private int N;
	
	public Ntuple(Object... objList)
	{
		this.objList = objList;
		N = objList.length;
	}

	public Object getkth(int k)
	{
		if (k < N)
		   return objList[k];
		else
		   return null;
	}
	
	public boolean equals(Ntuple tuple)
	{
		boolean itsEqual = true;
		
		for(int i = 0; i < N; i++)
		{
			itsEqual = itsEqual && (this.getkth(i).equals(tuple.getkth(i)));
			if(!itsEqual)
			   i = N;
		}
		return itsEqual;
	}

	public String toString()
	{
		String vargs = "Ntuple(";
		for(int i = 0; i < N; i++)
		{  if (i < N-1)
			  vargs += getkth(i) + "," ;
		   else
			  vargs += getkth(i) + ")";
		}
		return vargs;
	}
	
}
