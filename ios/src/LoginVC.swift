//
//  LoginVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import FirebaseAuth

class LoginVC: UIViewController {
    
    @IBOutlet weak var signinBtn: UIButton!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var emailField: UITextField!
    
    @IBAction func signinBtnPressed(_ sender: Any) {
        
        FirebaseAuth.Auth.auth().signIn(withEmail: emailField.text!, password: passwordField.text!)
        { (user, error) in
            if(error != nil){
                let alertVC = UIAlertController(title: "Error", message: "Invalid email or password, please try again.", preferredStyle: .alert)
                
                let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
                alertVC.addAction(alertActionCancel)
                self.present(alertVC, animated: true, completion: nil)
                print("INCORRECT signed in")
            }
            else{
                let userDefaults = UserDefaults.standard
            
                userDefaults.set(self.emailField.text, forKey: "email")
                userDefaults.set(self.passwordField.text, forKey: "password")
                userDefaults.set(false, forKey: "first")
                print("signed in")
                self.performSegue(withIdentifier: "signInToTabBar", sender: nil)
            }
        }
        
     
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
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action:#selector(LoginVC.dismissKeyboard))
        view.addGestureRecognizer(tap)
        // Do any additional setup after loading the view.
            }
    @objc func dismissKeyboard() {
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
