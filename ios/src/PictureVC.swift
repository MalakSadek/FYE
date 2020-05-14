//
//  PictureVC.swift
//  FYEApp
//
//  Created by Malak Sadek on 7/19/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit

class PictureVC: UIViewController {

    
    @IBOutlet weak var picture: ImageScrollView!
    var picurl:String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let url = NSURL(string: picurl) {
            if let data = NSData(contentsOf: url as URL) {
                
                let myImage = UIImage(data: data as Data)
                picture.display(image: myImage!)
                
            }        
        }
        
    
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

}
