# Clinic Management System - Requirements Checklist

## **⚠️ IMPORTANT CLARIFICATIONS**

### **✅ What You CAN Use:**
- **All Java classes and interfaces that are NOT collections:**
  - `String`, `Double`, `Character`, `Date`, `Timer`
  - `Scanner`, `GregorianCalendar`, `java.io.*`
  - `System.nanoTime()`, Exception handling classes
  - `Comparator`, `Comparable`, `java.util.Iterator`
  - Any other non-collection Java classes

### **❌ What You CANNOT Use:**
- **Java Collections Framework classes:**
  - `java.util.List`, `ArrayList`, `LinkedList`
  - `HashMap`, `HashSet`, `TreeMap`, `TreeSet`
  - Any other collection classes from `java.util.*`

---

## **A. Technical Requirements**

### **1. Collection ADT (Team Component)** ✅/❌

#### **Purpose & Objectives**:
- [x] **Understand collection ADTs** and different implementation ways
- [x] **Gain hands-on experience** creating own collection ADTs instead of using predefined Java classes
- [x] **Original Implementation**: Must create own collection ADTs with required characteristics and operations

#### **Implementation Requirements**:
- [x] **Generic Implementation**: ADT must be generic (`<T>`)
- [x] **No Java Collections Framework**: 
  - [x] No `java.util.List`, `ArrayList`, `LinkedList`, `HashMap`, etc.
  - [x] **CAN USE**: `Comparator` & `Comparable` (for sorting)
  - [x] **CAN USE**: `java.util.Iterator` 
  - [x] **CAN USE**: Any Java classes that are NOT collections:
    - [x] `String`, `Double`, `Character`, `Date`, `Timer`
    - [x] `Scanner`, `GregorianCalendar`, `java.io.*`
    - [x] `System.nanoTime()`, Exception handling classes
    - [x] All other non-collection Java classes and interfaces

#### **ADT Type Options** (Choose one):
- [x] **List** (List, Sorted List, etc.) ✅ **IMPLEMENTED**
- [ ] **Stack**
- [ ] **Queue** (Queue, Priority Queue, Deque)
- [ ] **Set** (Set, HashSet)
- [ ] **Binary Tree** (Binary Search Tree, AVL Tree)
- [ ] **Dictionary** (Dictionary, HashMap)
- [ ] **Others** as needed

#### **Implementation Standards**:
- [x] **Complete Operations**: Include all basic operations relevant to ADT type for completeness and reusability
- [x] **Source Acknowledgment**: Acknowledge source if adapting from sample code (at top of Java interface/class)
- [x] **Originality**: Using exact sample code without modification results in lower marks
- [x] **Team Unity**: Only one collection ADT for the entire team
- [x] **Extensibility**: Each member can extend it with extra methods
- [x] **Assessment**: Marks based on creativity, appropriateness, and correctness of ADT implementation

### **2. Module Implementation (Individual Component)** ✅/❌

- [x] **Unique Module**: Develop one unique module from:
  - [x] Patient Management ✅
  - [x] Doctor Management ✅
  - [x] Medical Treatment Management ✅ **NEWLY COMPLETED**
  - [ ] Consultation Management
  - [ ] Pharmacy Management
- [x] **ADT Usage**: Use team's collection ADT to store and manage data
- [x] **Two Summary Reports**: +                                            
  - [x] Meaningful formatting with title and printed date
  - [x] Clear field names
  - [x] Number of records displayed
- [x] **No Duplication**: No duplication of modules within team
- [x] **Data Population**: Populate missing data via:
  - [ ] File reading (optional)
  - [x] Hard-coded initialization methods in control classes
  - [x] Sample data loaded via ClinicInitializer (10 patients, 8 doctors, 5 treatments)

#### **Medical Treatment Management Module Details**:
- [x] **Functionality 1**: Create New Treatment Record (diagnosis, treatment plan, medication, cost)
- [x] **Functionality 2**: View All Treatment Records (formatted table display)
- [x] **Functionality 3**: Update Treatment Status (Active, Completed, Discontinued)
- [x] **Functionality 4**: Schedule Follow-up Appointment (with notes and dates)
- [x] **Report 1**: Treatment History Report (status distribution and cost analysis)
- [x] **Report 2**: Diagnosis Distribution Report (diagnosis type statistics)
- [x] **Entity Class**: MedicalTreatment with comprehensive attributes and business methods
- [x] **Control Class**: MedicalTreatmentManagement with all business logic
- [x] **Boundary Class**: MedicalTreatmentUI with user input/output handling
- [x] **Sample Data**: 5 sample treatment records with various diagnoses and statuses

### **3. Architecture (ECB Pattern)** ✅/❌

#### **Entity Classes**:
- [x] Data model with constructors, getters, setters, `toString`, `equals`
- [x] No user input/output in entities
- [x] May store relationships (1-to-many) using collection ADT

#### **Control Classes**:
- [x] Business logic implementation
- [x] Interacts with entity and boundary classes

#### **Boundary Classes**:
- [x] Handles user input/output
- [x] Interacts only with control classes

#### **DAO Classes (Optional)**:
- [ ] For file/database access (not required)

#### **Architecture Rules**:
- [x] Actors interact only with boundary classes
- [x] Boundary classes communicate only with control classes
- [x] Control classes communicate with boundary & entity classes
- [x] Entity classes communicate only with other entity classes

### **4. Coding Standards** ✅/❌

- [x] **Java CamelCase naming convention**
- [x] **Author name** at top of each file
- [x] **Acknowledge adapted code** if any
- [x] **Use `ListInterface<Entity>`** style variable declarations
- [x] **Manual sorting & searching** (bubble sort, insertion sort, etc.)
- [x] **Proper indentation and readability**

### **5. Reports** ✅/❌

- [x] **Not just listing records** - must involve data processing
- [x] **Use ADT methods** for filtering, counting, sorting
- [x] **Output format**:
  - [x] Report title
  - [x] Date/time generated
  - [x] Summary of results
  - [x] Total number of records shown

---

## **B. Deliverables**

### **Team Deliverables** ✅/❌
- [x] **ADT specification & implementation** (interface + class) ✅ **COMPLETED**
- [ ] **Integrated NetBeans project** (all members' modules)
- [x] **`ReadMe.txt`** with run instructions
- [ ] **Google Doc + PDF team report**

### **Individual Deliverables** ✅/❌
- [x] **Your module code** (entity, control, boundary)
- [x] **Your two reports**
- [x] **Contribution to team ADT**

---

## **C. Restrictions** ✅/❌

- [x] **No `Collection.sort()`** or prebuilt Java sorting methods
- [x] **No prebuilt collection classes** from Java Collections Framework
- [x] **No login/password system** required
- [x] **Only validations** that involve ADT method calls are required

---

## **D. Additional Assignment Criteria Requirements** ✅/❌

### **Core Functionality Requirements**:
- [x] **View/Display/List Output Functions**: Display current entity objects after performing functions (add new, edit, remove, merge) of your modules
- [x] **Table Form Display**: Display all initialized data in table form when starting module demo
- [x] **Data Initialization**: Initialize more data objects to demonstrate functionalities control
- [x] **Entity Dependencies**: Edit, Remove, Filter, Searching should have at least 2 or more Entities Dependency (Relationships)
- [x] **CRUD Control Effectiveness**: CRUD functions should have control effectiveness of ADT methods invoked (interface class) to perform functionalities of modules & control processing for entities objects in an innovative way

### **Entity Relationship Examples**:
- [x] **Multi-Class Dependencies**: Show 4+ classes dependency (e.g., Applicant → JobApplication ← Job ← Company)
- [x] **Automated Processing**: If number of successful candidates are less than 3, Merge function on interview schedules for same jobs application of a company in automated way or manually

### **ADT Usage Requirements**:
- [x] **Classes (shows relationships & dependency control)**
- [x] **Control class(es) for modules**
- [x] **Creativity & Complexity**: Performing functions, control dependency for entity objects in an automated/efficient way
- [x] **Dynamic Processing**: Processing entity data dynamically, list output & Control effectiveness of functionalities performed
- [x] **Dynamic Module Impact**: Amend, remove entity objects will affect modules dynamically
- [x] **No Static Data Focus**: Do NOT focus on Static data (e.g., amend applicant name, not affect the control)
- [x] **ADT Methods Validation**: ADT methods validation needed
- [x] **Complete CRUD**: Complete CRUD, display/list objects stored, add on new functions of your module

### **Current Implementation Status**:
- [x] **Patient → MedicalTreatment ← Doctor** (3-class dependency)
- [x] **Consultation → MedicalTreatment → Pharmacy** (3-class dependency)
- [x] **Dynamic Data Processing**: All modules use shared ADT lists from ClinicInitializer
- [x] **Automated Workflow**: Streamlined treatment workflow automatically links consultation to treatment to pharmacy
- [x] **ADT Method Usage**: Extensive use of custom ADT methods (add, remove, search, sort, filter)
- [x] **Table Display**: All modules display data in formatted table form
- [x] **CRUD Operations**: Complete Create, Read, Update, Delete operations implemented

---

## **E. Progress Tracking**

### **Current Status**:
- **Collection ADT**: [x] Complete [ ] In Progress [ ] Not Started
- **Module Implementation**: [x] Complete [ ] In Progress [ ] Not Started  
- **Architecture**: [x] Complete [ ] In Progress [ ] Not Started
- **Reports**: [x] Complete [ ] In Progress [ ] Not Started
- **Deliverables**: [ ] Complete [x] In Progress [ ] Not Started

### **Notes**:
- **Queue Functionality Successfully Integrated**: All queue operations now use the ADT's built-in queue methods
- **Patient Entity Simplified**: Removed queue-related fields and methods, now uses ADT queue functionality
- **Original Implementation**: Queue functionality is completely original and integrated into SortedArrayList
- **No External Queue Logic**: All queue management is now handled through the ADT
- **Medical Treatment Module Completed**: All 4 functionalities and 2 reports implemented successfully
- **Enhanced Main System**: ClinicManagementSystem now includes Medical Treatment Management module

---

## **E. File Structure Checklist**

### **Required Files**:
- [x] `src/adt/ListInterface.java`
- [x] `src/adt/SortedArrayList.java` (or your ADT implementation)
- [x] `src/entity/Patient.java`
- [x] `src/entity/Doctor.java`
- [x] `src/entity/MedicalTreatment.java` ✅ **NEW**
- [x] `src/control/PatientManagement.java`
- [x] `src/control/DoctorManagement.java`
- [x] `src/control/MedicalTreatmentManagement.java` ✅ **NEW**
- [x] `src/boundary/PatientManagementUI.java`
- [x] `src/boundary/DoctorManagementUI.java`
- [x] `src/boundary/MedicalTreatmentUI.java` ✅ **NEW**
- [x] `src/boundary/ClinicManagementSystem.java` (main application)
- [x] `src/dao/ClinicInitializer.java` (data initialization utility)
- [x] `ReadMe.txt`
- [x] `REQUIREMENTS_CHECKLIST.md` (this file)

### **Optional Files**:
- [ ] Test files in `test/` directory
- [ ] Additional utility classes
- [ ] Configuration files

---

**Last Updated**: 2024-12-19
**Team Members**: Wai Kin
**Your Module**: Patient Management, Doctor Management & Medical Treatment Management
**ADT Status**: ✅ **COMPLETED** - SortedArrayList with integrated queue functionality
**Modules Status**: ✅ **3 MODULES COMPLETED** - Patient, Doctor, and Medical Treatment Management
