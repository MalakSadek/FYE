//
//  PopUpVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/15/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit

class PopUpVC2: UIViewController {
    
    var info:String = ""
    
    @IBOutlet weak var labelView: UIView!
    @IBOutlet weak var dismissBtn: UIButton!
    @IBOutlet weak var popuplabel: UILabel!
    
    
    @IBAction func dismissBtnPressed(_ sender: Any) {
        animateOut()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        animateIn()
        // Do any additional setup after loading the view.
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func animateIn()
    {
        self.view.backgroundColor = UIColor.black.withAlphaComponent(0.2)
        labelView.backgroundColor = UIColor.white.withAlphaComponent(0.9)
        
        
        
        labelView.center = self.labelView.center
        labelView.transform = CGAffineTransform.init(scaleX: 1.3, y: 1.3)
        labelView.alpha = 0
        
        
        UIView.animate(withDuration: 0.2){
            self.labelView.alpha = 1
            self.labelView.transform = CGAffineTransform.identity
        }
        
        popuplabel.text = info
        labelView.layer.cornerRadius = 10
        dismissBtn.layer.cornerRadius = 10
        
        self.labelView.sizeToFit()
        
    }
    
    func animateOut()
    {
        UIView.animate(withDuration: 0.2, animations: {
            self.labelView.transform=CGAffineTransform.init(scaleX: 1.3, y: 1.3)
            self.labelView.alpha = 0
        }) {(sucess:Bool) in
            self.view.removeFromSuperview()
        }
    }
    
}
