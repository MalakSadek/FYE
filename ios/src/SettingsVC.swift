//
//  SettingsVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import FirebaseAuth

class SettingsVC: UIViewController {
    
    @IBOutlet weak var logoutBtn: UIButton!
    
    @IBOutlet weak var emaillbl: UILabel!
    @IBOutlet weak var namelbl: UILabel!
    
    var arrayOfData:NSArray = []
    var dictionaryOfData:NSDictionary = [:]
    
    @IBAction func changePassPressed(_ sender: Any) {
        let userDefaults = UserDefaults.standard
        
        Auth.auth().sendPasswordReset(withEmail: userDefaults.string(forKey: "email")!) { error in
            let alertVC = UIAlertController(title: "Password Reset", message: "Please check your email for instructions on how to reset your password.", preferredStyle: .alert)
            
            let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
            alertVC.addAction(alertActionCancel)
            self.present(alertVC, animated: true, completion: nil)
        }
        
    }
    @IBAction func logoutBtnPressed(_ sender: Any) {
        do {
            try  Auth.auth().signOut()
        } catch {
            print(error)
        }
        let userDefaults = UserDefaults.standard
        userDefaults.set(true, forKey: "first")
        let alertVC = UIAlertController(title: "Gone So Soon?", message: "Are you sure you want to log out?", preferredStyle: .alert)
        let alertActionOkay = UIAlertAction(title: "Yes, I'm Sure", style: .default) {
            (_) in
            self.performSegue(withIdentifier: "logoutToViewController", sender: self)
            self.userDefultsRemoveObjects()
        }
        alertVC.addAction(alertActionOkay)
        let alertActionCancel = UIAlertAction(title: "No, Cancel", style: .default, handler: nil)
        alertVC.addAction(alertActionCancel)
        self.present(alertVC, animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let userDefaults = UserDefaults.standard
        
        if (!userDefultsIsEmpty()) {
            
            let url_data = NSURL(string: "http://188.226.144.157/group1/SearchEmail.php")
            let dataOfURL_data = NSData(contentsOf: url_data! as URL)
            arrayOfData = try! JSONSerialization.jsonObject(with: dataOfURL_data! as Data, options: JSONSerialization.ReadingOptions.allowFragments) as! NSArray
            
            dictionaryOfData = arrayOfData[0] as! NSDictionary

            print(dictionaryOfData)
                        self.emaillbl.text = userDefaults.string(forKey: "email")!
                        self.namelbl.text = userDefaults.string(forKey: dictionaryOfData["Name"] as! String)!
     
        }
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    /////  USER DEFULT FUNCTIONS
    func userDefultsWriting(email:String, password:String, name:String){
        let userDefaults = UserDefaults.standard
        
        userDefaults.set(email, forKey: "email")
        userDefaults.set(password, forKey: "password")
        userDefaults.set(name, forKey: "name")
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
        if(UserDefaults.standard.object(forKey: "email") == nil){
            return true
        }
        if(UserDefaults.standard.object(forKey: "name") == nil){
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
    
    
    func makePopUp(label: String) {
        let popUpVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popUpID") as! PopUpVC
        
        self.addChild(popUpVC)
        popUpVC.view.frame = self.view.frame
        self.view.addSubview(popUpVC.view)
        popUpVC.didMove(toParent: self)
        
        popUpVC.popUpLabel.text = label
        print(popUpVC.info)
        
        
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
