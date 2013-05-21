package stanly.server.Analysis.Model.Relation.Type;

public enum NodeRelationLevel {
	PackageToPackage,	// 패키지 및 패키지 셋들의 관계를 정의할 
	ClassToClass,	// 클래스 또는 인터페이스간의 관계를 정의할 
	MetohdToField,	// 메소드 및 필드 또는 메소드 간의 관계를 정의할 때 현재 사용 안
	LibraryToLibrary// 라이브러리간의 관계를 정의할 때 
}
