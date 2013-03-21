package stanly.server.MetricView.Json;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class CodeSizeValue {
		@Expose
		private String name;
		@Expose
		private Integer value;
		@Expose
		private ArrayList<CodeSizeValue> children;

		private int NSleft;
		private int NSRight;
		
		public CodeSizeValue(String name, int nsLeft, int nsRight) {
			this.name = name;
			this.value=null;
			this.NSleft=nsLeft;
			this.NSRight=nsRight;
			children = new ArrayList<CodeSizeValue>();
		}
		/**
		 * @param name
		 * @param value
		 */
		public CodeSizeValue(String name, Integer value, int nsLeft, int nsRight) {
			super();
			this.name = name;
			this.value = value;
			this.NSleft=nsLeft;
			this.NSRight=nsRight;
			children = null;
		}
		

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public CodeSizeValue getChildrenNode(int i) {
			return children.get(i);
		}	
		public boolean addChildNode(CodeSizeValue e)
		{
			return children.add(e);	
		}
		public int getNSleft() {
			return NSleft;
		}
		public int getNSRight() {
			return NSRight;
		}
		
		
		
		
}
