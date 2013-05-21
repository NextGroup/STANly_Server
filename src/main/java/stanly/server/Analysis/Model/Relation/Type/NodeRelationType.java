package stanly.server.Analysis.Model.Relation.Type;

/**
 * @author Karuana
 *	노드간의 관계 종류를 나타내는 enum이다. 
 */
public enum NodeRelationType {
	EXTENDS, 
	IMPLEMENTS,
	CONTAINS, 
	RETURNS, 
	HAS_PARAM,
	THROWS, 
	CALLS,
	ACCESSES,
	IS_OF_TYPE,
	REFERENCESE
}
