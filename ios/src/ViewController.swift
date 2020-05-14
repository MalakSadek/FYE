import UIKit
import Firebase

class ViewController: UIViewController {
    
    
    @IBOutlet weak var loginBtn: UIButton!
    
    @IBOutlet weak var signupBtn: UIButton!
    

    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        
        if (!userDefultsIsEmpty())
            
        {
            
            let values = userDefultsReading()
            let email = values[0]
            let password = values[1]
            
            
            FirebaseAuth.Auth.auth().signIn(withEmail: email, password: password)
            { (user, error) in
                if(error != nil){
                    print("INCORRECT view controller sign in")
                    self.userDefultsRemoveObjects()
                    
                }else{
                    self.performSegue(withIdentifier: "ViewControllerToTabBar", sender: nil)
                }
            }
            
            
        }
        
        // Do any additional setup after loading the view, typically from a nib.
        
    }
    
    func userDefultsIsEmpty()-> Bool{
    let userDefaults = UserDefaults.standard
    if(userDefaults.object(forKey: "email") == nil &&
    userDefaults.object(forKey: "password") == nil &&
    userDefaults.object(forKey: "name") == nil ){
    return true
    }
    return false
}

func userDefultsReading()-> [String]{
    let userDefaults = UserDefaults.standard
    
    let email = userDefaults.string(forKey: "email")!
    let password = userDefaults.string(forKey: "password")!
    let values = [email,password]
    
    
    return values
}


func userDefultsRemoveObjects() {
    
    let userDefaults = UserDefaults.standard
    
    userDefaults.removeObject(forKey: "name")
    userDefaults.removeObject(forKey: "password")
    userDefaults.removeObject(forKey: "email")
    
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
    
    
    
}
