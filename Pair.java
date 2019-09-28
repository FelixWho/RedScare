/********  Pair.java *********
 * 
 *  APCS Project 2011-2017
 *  Computational Biology
 *  Dr. John Pais 
 *  pais.john@gmail.com
 *
 */


    public class Pair
	{
		private Object obj1, obj2;
		
		public Pair(Object obj1, Object obj2)
		{
			this.obj1 = obj1;
			this.obj2 = obj2;
		}

		public Object get1st()
		{
			return obj1;
		}
		
		public Object get2nd()
		{
			return obj2;
		}
		
		public boolean equals(Pair pair)
		{
			return (this.get1st().equals(pair.get1st()) && this.get2nd().equals(pair.get2nd()));
		}

		public String toString()
		{
			return "Pair(" + obj1 + "," + obj2 + ")";
		}
	}
