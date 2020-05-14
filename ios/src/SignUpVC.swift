//
//  SignUpVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import FirebaseAuth

class SignUpVC: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "signUpToHelpVC"){
            let destVC: help2VC=segue.destination as! help2VC
    }
    }

    @available(iOS 2.0, *)
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    @available(iOS 2.0, *)
    func pickerView(_ pickerView: UIPickerView,
                    titleForRow row: Int,
                    forComponent component: Int) -> String!
    {
        if (pickerView == house){
            
            return Houses[row] as! String;
            
        }
            
        if (pickerView == Group){
            
            return groupsstring[row] as! String;
            
        }
        return "0"
    }
    
    @available(iOS 2.0, *)
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if (pickerView == house){
            grouptxt.isHidden = false;
            Group.isHidden = false;
            chosenhouse = Houses[row] as! String
            groupsstring = (Groups[row] as! String).split{$0 == ","}.map(String.init)
            Group.reloadAllComponents()
            print(groupsstring)
        }
        else if (pickerView == Group){
            chosengroup = groupsstring[row] as! String
        }
    }
    
    @IBOutlet weak var signupBtn: UIButton!
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var verifyPasswordField: UITextField!
    @IBOutlet weak var verify: UIButton!
    var Houses:NSArray = []
    var Groups:NSArray = []
    var housesstring:[String] = []
    var groupsstring:[String] = []
    @IBOutlet weak var housetxt: UILabel!
    @IBOutlet weak var fye: UISegmentedControl!
    var one:String = ""
    @IBOutlet weak var grouptxt: UILabel!
    @IBOutlet weak var house: UIPickerView!
    @IBOutlet weak var Group: UIPickerView!
    var first: Bool = true
    var isFYE: Bool = false
    var chosenhouse:String = ""
    var chosengroup:String = ""
    var dictionaryOfHouses:NSDictionary = [:]
    var dictionaryOfGroups:NSDictionary = [:]
    
    @IBAction func FYEChanged(_ sender: UISegmentedControl) {
        switch sender.selectedSegmentIndex {
        case 0:
            grouptxt.isHidden = true;
            housetxt.isHidden = true;
            Group.isHidden = true;
            house.isHidden = true;
            break;
             break;
        case 1:
                housetxt.isHidden = false;
                house.isHidden = false;
                isFYE = true;
                break;
        default:
            break;
    }
    }
    @IBAction func verifyPressed(_ sender: Any) {
        
        Auth.auth().signIn(withEmail: self.emailField.text!, password: self.passwordField.text!) {
            (user, error) in
            if let user = Auth.auth().currentUser {
                if !user.isEmailVerified{
                    ///////POP UP CODE STARTS HEREEEEEEEEEEEEE
                    let alertVC = UIAlertController(title: "Error", message: "Sorry. Your email address has not yet been verified. Do you want us to send another verification email to "+self.emailField.text!, preferredStyle: .alert)
                    let alertActionOkay = UIAlertAction(title: "Send", style: .default) {
                        (_) in
                        user.sendEmailVerification(completion: nil)
                    }
                    let alertActionCancel = UIAlertAction(title: "Cancel", style: .default, handler: nil)
                    
                    alertVC.addAction(alertActionOkay)
                    alertVC.addAction(alertActionCancel)
                    self.present(alertVC, animated: true, completion: nil)
                    //////POP UP CODE ENDS HEREEEEEEEEEEEEE
                } else {
                     self.first = false;
                     let userDefaults = UserDefaults.standard
                    userDefaults.set(self.first, forKey: "first")
                    
                    let url: NSURL = NSURL(string: "http://188.226.144.157/group1/AddUser.php?email="+self.emailField.text!+"&password="+self.passwordField.text!+"&houseData="+self.chosenhouse+"&groupData="+self.chosengroup)!
                    let request:NSMutableURLRequest = NSMutableURLRequest(url:url as URL)
                    
                    let bodyData = "name="+self.nameField.text!
                    
                    request.httpMethod = "POST"
                    
                    request.httpBody = bodyData.data(using: String.Encoding.utf8);
                    NSURLConnection.sendAsynchronousRequest(request as URLRequest, queue: OperationQueue.main)
                    {
                        (response, data, error) in
                        print(response)
                        
                        if let HTTPResponse = response as? HTTPURLResponse {
                            let statusCode = HTTPResponse.statusCode
                            
                            if statusCode == 200 {
                                
                                self.userDefultsWriting(email: (self.emailField.text)!,
                                                        password: (self.passwordField.text)!,
                                                        name: (self.nameField.text)!,
                                                        groupdata:(self.chosengroup),
                                                        housedata:(self.chosenhouse))
                               
                                print ("Email verified. Signing in...")
                                self.performSegue(withIdentifier: "signUpToHelpVC", sender: nil)
                            }
                        }
                    }
                }
            }
        }

    }
    
    @IBAction func signBtnPressed(_ sender: Any) {
        print(nameField.text! as String)
        
        if( !(nameField.text?.isEmpty)! &&
            !(emailField.text?.isEmpty)! &&
            !(passwordField.text?.isEmpty)! &&
            !(verifyPasswordField.text?.isEmpty)!){
            
            

            if(passwordField.text != verifyPasswordField.text){
                let alertVC = UIAlertController(title: "Error", message: "Passwords do not match.", preferredStyle: .alert)
    
                let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
                alertVC.addAction(alertActionCancel)
                self.present(alertVC, animated: true, completion: nil)
            }else if(!(emailField.text?.contains("@aucegypt.edu"))! ||
                (emailField.text?.count)! <= "@aucegypt.edu".count){
                let alertVC = UIAlertController(title: "Error", message: "Must sign up with AUC email.", preferredStyle: .alert)
                
                let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
                alertVC.addAction(alertActionCancel)
                self.present(alertVC, animated: true, completion: nil)
            }
            else{
                if(emailField.text?.contains(" "))!{
                    emailField.text?.remove(at: (emailField.text?.index(of: " "))!)
                }
                
                    FirebaseAuth.Auth.auth().createUser(withEmail: emailField.text!,
                                                    password: passwordField.text!)
                { (user, error) in
                    
                    if(error != nil){
                        let alertVC = UIAlertController(title: "Error", message: "Account already exists", preferredStyle: .alert)
                        
                        let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
                        alertVC.addAction(alertActionCancel)
                        self.present(alertVC, animated: true, completion: nil)
                    }else{
                        print("signed up")
                        Auth.auth().signIn(withEmail: self.emailField.text!, password: self.passwordField.text!) {
                            (user, error) in
                            if let user = Auth.auth().currentUser {
                                
                                user.sendEmailVerification(completion: nil)
                                self.verify.isEnabled = true;
                                let alertVC = UIAlertController(title: "Done", message: "Please click on the link in the verification email sent to you now.", preferredStyle: .alert)
                                
                                let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
                                alertVC.addAction(alertActionCancel)
                                self.present(alertVC, animated: true, completion: nil)
                            }
                        }
                            }
                        }
            
                    }
            
            
        }else {
            if((nameField.text?.isEmpty)!){
                nameField.placeholder = "Can't be empty"
            }
            if((nameField.text?.isEmpty)!){
                nameField.placeholder = "Can't be empty"
            }
            if((emailField.text?.isEmpty)!){
                passwordField.placeholder = "Can't be empty"
            }
            if((passwordField.text?.isEmpty)!){
                verifyPasswordField.placeholder = "Can't be empty"
            }
            if((verifyPasswordField.text?.isEmpty)!){
                emailField.placeholder = "Can't be empty"
            }
        }
        
    }
    
    func makePopUp(label: String) {
        let popUpVC2 = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popUp2ID") as! PopUpVC2
        
        self.addChild(popUpVC2)
        popUpVC2.view.frame = self.view.frame
        self.view.addSubview(popUpVC2.view)
        popUpVC2.didMove(toParent: self)
        
        popUpVC2.popuplabel.text = label
        print(popUpVC2.info)


    }
    
    /////  USER DEFULT FUNCTIONS
    func userDefultsWriting(email:String, password:String, name:String, groupdata:String, housedata:String){
        let userDefaults = UserDefaults.standard
        
        userDefaults.set(email, forKey: "email")
        userDefaults.set(password, forKey: "password")
        userDefaults.set(name, forKey: "name")
        
        if(isFYE){
        userDefaults.set(housedata, forKey: "housedata")
        userDefaults.set(groupdata, forKey: "groupdata")
        }
        else{
            userDefaults.set("None", forKey: "housedata")
            userDefaults.set("None", forKey: "groupdata")
            chosenhouse = "None"
            chosengroup = "None"
        }
    }
    
    func userDefultsReading()-> [String]{
        let userDefaults = UserDefaults.standard
        
        let email = userDefaults.string(forKey: "email")!
        let password = userDefaults.string(forKey: "password")!
        let name = userDefaults.string(forKey: "name")!
        let values = [email,password,name]
    

        return values 
    }
    
    func userDefultsIsEmpty()-> Bool{
         let userDefaults = UserDefaults.standard
        if(userDefaults.object(forKey: "email") == nil){
            return true
        }
        return false
    }
    
    func userDefultsRemoveObjects() {
        
        let userDefaults = UserDefaults.standard
        
        userDefaults.removeObject(forKey: "name")
        userDefaults.removeObject(forKey: "password")
        userDefaults.removeObject(forKey: "email")
        
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    // The number of rows of data
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if (pickerView == house){
            
            return Houses.count;
            
        }
            
        if (pickerView == Group){
            
            return groupsstring.count;
            
        }
        return 0
    }
    
    // The data to return for the row and component (column) that's being passed in
  
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.house.delegate = self
        self.house.dataSource = self
        self.Group.delegate = self
        self.Group.dataSource = self
        
        grouptxt.isHidden = true;
        housetxt.isHidden = true;
        Group.isHidden = true;
        house.isHidden = true;
        
        let url1 = NSURL(string: "http://188.226.144.157/group1/FetchHouses.php")
        let dataOfURL1 = NSData(contentsOf: url1! as URL)
        
        Houses = try! JSONSerialization.jsonObject(with: dataOfURL1! as Data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSArray
        
        let url2 = NSURL(string: "http://188.226.144.157/group1/FetchGroups.php")
        let dataOfURL2 = NSData(contentsOf: url2! as URL)
        
        Groups = try! JSONSerialization.jsonObject(with: dataOfURL2! as Data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSArray
        
    
        print (groupsstring)
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action:#selector(SignUpVC.dismissKeyboard))
        view.addGestureRecognizer(tap)
        
        if(!userDefultsIsEmpty()){
        
        let vals = userDefultsReading()
            print(vals)
        }
        // Do any additional setup after loading the view.
    }
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        UIApplication.shared.statusBarStyle = .lightContent
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
    }

    open override var shouldAutorotate: Bool {
        get {
            return false
        }
    }
    
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        get {
            return .portrait
        }
    }
   
}
