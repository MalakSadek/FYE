//
//  CompetitionVC.swift
//  FYEApp
//
//  Created by Malak Sadek on 7/18/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit

class CompetitionVC: UIViewController {

   var info:String = ""
   var house:String = ""
   var dividedinfo:[String] = []
    
    @IBOutlet weak var titlelbl: UILabel!
    @IBOutlet weak var thirdlbl: UILabel!
    @IBOutlet weak var secondlbl: UILabel!
    @IBOutlet weak var firstlbl: UILabel!
    @IBOutlet weak var rankingtxt: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        titlelbl.text = "Winners for house "+house+":"
        dividedinfo = info.split{$0 == ","}.map(String.init)
        firstlbl.text = dividedinfo[0]
        secondlbl.text = dividedinfo[1]
        thirdlbl.text = dividedinfo[2]
        rankingtxt.text = dividedinfo[3]
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
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
